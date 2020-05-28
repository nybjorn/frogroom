package info.nyberg.frogroom.controllers;

import info.nyberg.frogroom.models.Frog;
import info.nyberg.frogroom.repositories.FrogRepositry;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/frogs")
public class FrogsController {
  @Autowired private FrogRepositry frogRepositry;

  @GetMapping
  public List<Frog> list() {
    return frogRepositry.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Frog get(@PathVariable Long id) {
    return frogRepositry.getOne(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Frog create(@RequestBody final Frog frog) {
    return frogRepositry.saveAndFlush(frog);
  }

  @PutMapping("id")
  public Frog update(@PathVariable Long id, @RequestBody Frog frog) {
    Frog existingFrog = frogRepositry.getOne(id);
    BeanUtils.copyProperties(frog, existingFrog, "frogId");
    return frogRepositry.saveAndFlush(existingFrog);
  }

  /*
  HTTP DELETE
      A successful response of DELETE requests SHOULD be HTTP response code 200 (OK) if the response includes an entity describing the status, 202 (Accepted) if the action has been queued, or 204 (No Content) if the action has been performed but the response does not include an entity.
   */
  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    // think about the children
    frogRepositry.deleteById(id);
  }

  @PatchMapping(value = "{id}")
  public Frog patch(@PathVariable Long id, @RequestBody Frog frog) {
    Frog existingFrog = frogRepositry.getOne(id);
    BeanUtils.copyProperties(frog, existingFrog, "null");
    return frogRepositry.saveAndFlush(existingFrog);
  }
}
