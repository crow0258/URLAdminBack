package com.zero.beg.urladmin.common.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PathUtil {
    public String getHomePath(){
        ApplicationHome h = new ApplicationHome(getClass());
        File jarFile = h.getSource();
        return jarFile.getAbsolutePath();
    }
}
