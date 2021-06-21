package springboard.example.webapp.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboard.example.bean.Stormtrooper;
import springboard.example.service.StormtrooperService;
import springboard.web.exception.NotFoundException;

import java.util.Collection;

@RestController
@RequestMapping("/troopers")
public class StormtrooperController {

    @DubboReference
    StormtrooperService stormtrooperService;

    @RequiresPermissions("troopers:read")
    @GetMapping
    public Collection<Stormtrooper> listTroopers() {
        return stormtrooperService.listStormtroopers();
    }

    @RequiresPermissions("troopers:read")
    @GetMapping(path = "/{id}")
    public Stormtrooper getTrooper(@PathVariable("id") String id) {
        Stormtrooper stormtrooper = stormtrooperService.getStormtrooper(id);
        if (stormtrooper == null) {
            throw new NotFoundException(id);
        }
        return stormtrooper;
    }

    @RequiresPermissions("troopers:create")
    @PostMapping
    public Stormtrooper createTrooper(@RequestBody Stormtrooper trooper) {
        return stormtrooperService.addStormtrooper(trooper);
    }

    @RequiresPermissions("troopers:update")
    @PostMapping(path = "/{id}")
    public Stormtrooper updateTrooper(@PathVariable("id") String id,
                                      @RequestBody Stormtrooper updatedTrooper) throws NotFoundException {
        return stormtrooperService.updateStormtrooper(id, updatedTrooper);
    }

    @RequiresPermissions("troopers:delete")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTrooper(@PathVariable("id") String id) {
        stormtrooperService.deleteStormtrooper(id);
    }

}
