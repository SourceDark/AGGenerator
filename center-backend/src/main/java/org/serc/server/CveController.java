package org.serc.server;

import java.util.List;

import org.serc.exception.ResourceNotFoundException;
import org.serc.network.controller.dto.CveEntry;
import org.serc.network.support.NetworkUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/cve/{cveId}")
public class CveController {
    
    @GetMapping("")
    public CveEntry cveEntry(@PathVariable String cveId) {
        List<CveEntry> cves = NetworkUtils.getCves(cveId);
        if(cves == null || cves.isEmpty()) {
            throw new ResourceNotFoundException("vulnerability not found");
        }
        return cves.get(0);
    }

}
