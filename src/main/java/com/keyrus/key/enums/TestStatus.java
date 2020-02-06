package com.keyrus.key.enums;

public enum TestStatus {

    ACTIVE(0),
    PASSED(2),
    FAILED(3),
    INCONCLUSIVE(4),
    TIMEOUT(5),
    ABORTED(6),
    BLOCKED(7),
    WARNING(9),
    ERROR(10),
    NOTAPPLICABLE(11);

    private final int valueAzure;

    TestStatus(int valueAzure){
        this.valueAzure = valueAzure;
    }

    public int getValueVSTS(){
        return this.valueAzure;
    }

}
