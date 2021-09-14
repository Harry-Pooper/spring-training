package main.java.config;

import com.sun.glass.ui.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * Created by mrlz on 10.04.2018.
 */
public class ProjectProperties {
    //todo - Сделать не так тупорыло
    public static File profilePicturesPathDir = new File("C:" + File.separator + "project" + File.separator + "jobtoad" + File.separator + "web" + File.separator + "img");
    public static File profileWarImgPathDir = new File("C:" + File.separator + "project" + File.separator + "jobtoad" + File.separator + "out" +
            File.separator + "artifacts" + File.separator + "JobToad_war_exploded" + File.separator + "img");
}
