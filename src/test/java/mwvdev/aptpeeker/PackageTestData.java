package mwvdev.aptpeeker;

import java.util.Arrays;
import java.util.List;

public final class PackageTestData {

    public static List<String> getPackages() {
        return Arrays.asList(
                "libsystemd0 229-4ubuntu21.2",
                "libpam-systemd 229-4ubuntu21.2",
                "systemd 229-4ubuntu21.2");
    }

    private PackageTestData() {

    }
}
