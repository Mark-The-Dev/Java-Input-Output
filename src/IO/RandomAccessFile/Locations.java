package IO.RandomAccessFile;

import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<Integer, Location>();
    private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private static RandomAccessFile ra;

    public static void main(String[] args) throws IOException {

        // rwd means allowing reading and writing and also that writes will occur synchronously.
        // It's important to let the random access file handle the synchronous writing especially in multiple threaded applications.
        try (RandomAccessFile rao = new RandomAccessFile("locations_rand.dat", "rwd")){
            rao.writeInt(locations.size());
            // each index record will store 3 integers: The locationId, The offset for location, and the size of the location length.

            // multiply the size by 3 (three records) multiply that by the size of integer by bytes.
            int indexSize = locations.size() * 3 * Integer.BYTES;

            // calculate the current position of the file pointer to the index size to account for the value we have already written to the file.
            // We also have to account for the integer that we are about to write to the file the location offset we just calculated.
            // This will give us the offset for the location section.
            int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.BYTES);
            rao.writeInt(locationStart);

            long indexStart = rao.getFilePointer();

            // we use this value to calculate the locations records length after writing to the file.
            int startPointer = locationStart;

            // we use this to move the file pointer to the first locations offset -- only needed for the first location because after all the data is written sequentially
            rao.seek(startPointer);

            // RandomAccessFile can not read / write objects :(
            // Can not be chained with a buffered reader.
            for(Location location: locations.values()){
                rao.writeInt(location.getLocationID());
                rao.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();
                for (String direction: location.getExits().keySet()){
                   if (!direction.equalsIgnoreCase("Q")){
                       builder.append(direction);
                       builder.append(",");
                       builder.append(location.getExits().get(direction));
                       builder.append(",");
                       // direction, locationId, direction, locationID
                       // IE: N,1,U,2
                   }

                }
                // writes the data
                rao.writeUTF(builder.toString());

                // creates index record.
                // record length = current position of file pointer and then deducting the startPointer.
                IndexRecord record = new IndexRecord(startPointer, (int) rao.getFilePointer() - startPointer);

                // adding the index record.
                index.put(location.getLocationID(), record);

                // update the start pointer for the next location
                startPointer = (int) rao.getFilePointer();

            }

            // seek the offset
            rao.seek(indexStart);

            // loop through index records and write to file.
            for(Integer locationID : index.keySet()){
                rao.writeInt(locationID);
                rao.writeInt(index.get(locationID).getStartByte());
                rao.writeInt(index.get(locationID).getLength());

            }

        }

    }

        // Using Random Access File to load!
        // 1. The first four bytes will contain the number of locations (bytes 0-3)
        // 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
        // 3. The next section of the file will contain the index (the index is 1692 bytes long. It will start at byte 8 and end at byte 1699)
        // 4. The final section of the file will contain the location records (The data). It will start at byte 1700.



    static {

            try {
                ra = new RandomAccessFile("locations_rand.dat", "rwd");
                // future reference if needed -- can show number of records.
                int numLocations = ra.readInt();
                long locationStartPoint = ra.readInt();

                while(ra.getFilePointer() < locationStartPoint) {
                    int locationId = ra.readInt();
                    int locationStart = ra.readInt();
                    int locationLength = ra.readInt();

                    IndexRecord record = new IndexRecord(locationStart, locationLength);
                    index.put(locationId, record);
                }

            } catch(IOException e) {
                System.out.println("IOException in static initializer: " + e.getMessage());
            }

    }

    public Location getLocation(int locationId) throws IOException {

        IndexRecord record = index.get(locationId);
        ra.seek(record.getStartByte());
        int id = ra.readInt();
        String description = ra.readUTF();
        String exits = ra.readUTF();
        String[] exitPart = exits.split(",");

        Location location = new Location(locationId, description, null);

        if(locationId != 0) {
            for(int i=0; i<exitPart.length; i++) {
                System.out.println("exitPart = " + exitPart[i]);
                System.out.println("exitPart[+1] = " + exitPart[i+1]);
                String direction = exitPart[i];
                int destination = Integer.parseInt(exitPart[++i]);
                location.addExit(direction, destination);
            }
        }

        return location;
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }

    public void close() throws IOException {
        ra.close();
    }
}

