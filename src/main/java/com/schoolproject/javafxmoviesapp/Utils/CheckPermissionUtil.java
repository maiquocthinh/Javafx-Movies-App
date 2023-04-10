package com.schoolproject.javafxmoviesapp.Utils;

import java.util.List;

public final class CheckPermissionUtil {
    private static CheckPermissionUtil instance = null;

    public static CheckPermissionUtil getInstance() {
        if (instance == null) instance = new CheckPermissionUtil();
        return instance;
    }

    public boolean check(String permission) {
        return AppSessionUtil.getInstance().getRole().getPermissions().contains(permission);
    }

    public boolean check(List<String> permissions) {
        return AppSessionUtil.getInstance().getRole().getPermissions().containsAll(permissions);
    }

}
