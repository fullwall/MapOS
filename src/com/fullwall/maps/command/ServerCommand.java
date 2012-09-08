package com.fullwall.maps.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A marker annotation that marks a method as being usable from the command
 * line.
 * 
 * @author fullwall
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerCommand {

}
