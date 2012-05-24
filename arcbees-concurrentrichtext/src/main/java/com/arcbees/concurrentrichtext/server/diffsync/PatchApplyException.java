package com.arcbees.concurrentrichtext.server.diffsync;

public class PatchApplyException extends Exception {

    private final int version;
    private final int targetVersion;
    private final String text;

    private static final long serialVersionUID = 20506879828452210L;

    public PatchApplyException(String text, int targetVersion, int version) {
        this.text = text;
        this.targetVersion = targetVersion;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public int getTargetVersion() {
        return targetVersion;
    }

    public String getRestoreText() {
        return text;
    }

}
