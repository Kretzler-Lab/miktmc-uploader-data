package org.miktmc.monitor;

import jakarta.servlet.http.HttpServletRequest;
import org.miktmc.logging.LoggingService;
import org.miktmc.packages.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MonitorController {

    private PackageService packageService;
    private LoggingService logger;

    @Autowired
    public MonitorController(PackageService packageService, LoggingService logger) {
        this.packageService = packageService;
        this.logger = logger;
    }

    @RequestMapping(value = "/v1/status", method = RequestMethod.GET)
    public @ResponseBody String getStatus(HttpServletRequest request) throws IOException {
        String status = "Status OK. " + packageService.findAllPackages().size() + " packages found.";
        logger.logInfoMessage(this.getClass(), null, status, request);
        return status;
    }
}
