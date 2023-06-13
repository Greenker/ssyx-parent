package com.molloi.ssyx.acl.utils;

import com.molloi.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Molloi
 * @date 2023/6/10 19:49
 */
public class PermissionHelper {

    public static List<Permission> buildTree(List<Permission> allList) {

        List<Permission> trees = new ArrayList<>();
        for (Permission permission : allList) {
            if (permission.getPid() == 0) {
                permission.setLevel(1);
                trees.add(findChildren(permission, allList));
            }
        }
        return trees;
    }

    private static Permission findChildren(Permission permission, List<Permission> allList) {
        permission.setChildren(new ArrayList<>());
        for (Permission it : allList) {
            if (permission.getId() == it.getPid()) {
                int level = permission.getLevel() + 1;
                it.setLevel(level);
                permission.getChildren().add(findChildren(it,allList));
            }
        }
        return permission;
    }

}
