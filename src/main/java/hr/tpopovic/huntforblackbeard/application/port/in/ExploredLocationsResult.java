package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class ExploredLocationsResult {

    private ExploredLocationsResult() {
    }

    public static final class Success extends ExploredLocationsResult {

        private final Integer exploredLocationsCount;
        private final Integer neededLocationsCount;

        public Success(Integer exploredLocationsCount, Integer neededLocationsCount) {
            this.exploredLocationsCount = exploredLocationsCount;
            this.neededLocationsCount = neededLocationsCount;
        }

        public Integer getExploredLocationsCount() {
            return exploredLocationsCount;
        }

        public Integer getNeededLocationsCount() {
            return neededLocationsCount;
        }

    }

    public static final class Failure extends ExploredLocationsResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
