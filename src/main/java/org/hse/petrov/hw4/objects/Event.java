package org.hse.petrov.hw4.objects;

import java.sql.Timestamp;
import java.time.Instant;
import java.sql.Date;

public class Event {

    private String ipAddress;
    private Timestamp timestamp;
    private String url;
    private Integer pageSize;
    private Integer statusCode;
    private String userAgent;

    public Event(String ipAddress, long timestamp, String url, Integer pageSize, Integer statusCode, String userAgent) {
        this.ipAddress = ipAddress;
        this.timestamp = new Timestamp(timestamp);
        this.url = url;
        this.pageSize = pageSize;
        this.statusCode = statusCode;
        this.userAgent = userAgent;
    }

    public static Event parseLine(String line) {

        String[] arr = line.split("\\s+");
        return new Event(
                arr[0],
                Long.valueOf(arr[1]),////TODO
                arr[2],
                Integer.valueOf(arr[3]),
                Integer.valueOf(arr[4]),
                arr[5]
        );

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!ipAddress.equals(event.ipAddress)) return false;
        if (!timestamp.equals(event.timestamp)) return false;
        if (!url.equals(event.url)) return false;
        if (!pageSize.equals(event.pageSize)) return false;
        if (!statusCode.equals(event.statusCode)) return false;
        return userAgent.equals(event.userAgent);

    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + pageSize.hashCode();
        result = 31 * result + statusCode.hashCode();
        result = 31 * result + userAgent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ipAddress='" + ipAddress + '\'' +
                ", timestamp=" + timestamp +
                ", url='" + url + '\'' +
                ", pageSize=" + pageSize +
                ", statusCode=" + statusCode +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
