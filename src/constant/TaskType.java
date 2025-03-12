package constant;

public enum TaskType {
    TASK,
    EPIC,
    SUB_TASK;

    @Override
    public String toString() {
        switch (this) {
            case TASK -> {
                return "TASK";
            }

            case EPIC -> {
                return "EPIC";
            }

            case SUB_TASK -> {
                return "SUB_TASK";
            }

            default -> throw new IllegalArgumentException();
        }
    }
}

