package org.hse.petrov.hw4.objects;

public class Location {

    private String ipAddress;
    private String region;

    public Location(String ipAddress, String region) {
        this.ipAddress = ipAddress;
        this.region = region;
    }

    public static Location parseLine(String line) {
        String[] arr = line.split("\\s+");
        return new Location(
                arr[0],
                arr[1]
        );
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (!ipAddress.equals(location.ipAddress)) return false;
        return region.equals(location.region);

    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + region.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "ipAddress='" + ipAddress + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
