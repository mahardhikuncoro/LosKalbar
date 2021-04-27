package base.location.network;

import java.util.List;

public class RoadsCallback {

    private List<RoadsSnappedPoints> snappedPoints;

    public List<RoadsSnappedPoints> getSnappedPoints() {
        return snappedPoints;
    }

    public class RoadsSnappedPoints {
        private RoadsLocation location;
        private Integer originalIndex;
        private String placeId;

        public RoadsLocation getLocation() {
            return location;
        }

        public Integer getOriginalIndex() {
            return originalIndex;
        }

        public String getPlaceId() {
            return placeId;
        }

        public class RoadsLocation {
            private String latitude;
            private String longitude;

            public String getLatitude() {
                return latitude;
            }

            public String getLongitude() {
                return longitude;
            }
        }

    }

}