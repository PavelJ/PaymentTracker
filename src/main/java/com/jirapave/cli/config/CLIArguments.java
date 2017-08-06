package com.jirapave.cli.config;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * CLI API specification, we will NOT validate wrong params
 */
@Data
public class CLIArguments {

    @Parameter(names = { "--file", "-f" }, description = "Input file for initialization of currency store")
    private String initFile = null;

    @Parameter(names = { "--help", "-h" }, help = true, description = "Prints CLI options")
    private boolean help = false;

}
