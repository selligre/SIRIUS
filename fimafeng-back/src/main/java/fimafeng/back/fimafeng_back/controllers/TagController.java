package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.Tag;
import fimafeng.back.fimafeng_back.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("all")
    public ResponseEntity<List<Tag>> findAll() {
        return new ResponseEntity<>(tagService.findAll(), HttpStatus.OK);
    }
}
