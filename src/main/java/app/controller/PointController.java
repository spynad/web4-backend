package app.controller;

import app.exception.PointNotFoundException;
import app.model.PointModelAssembler;
import app.repository.PointRepository;
import app.model.Point;
import com.google.common.collect.Streams;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PointController {

    private final PointRepository repository;
    private final PointModelAssembler assembler;

    public PointController(PointRepository repository, PointModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/points")
    public CollectionModel<EntityModel<Point>> getAllPoints() {
        List<EntityModel<Point>> points = Streams.stream(repository.findAll())
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(points,
                linkTo(methodOn(PointController.class).getAllPoints()).withSelfRel());
    }

    @GetMapping("/points/{id}")
    public EntityModel<Point> getOnePoint(@PathVariable long id) {
        Point point = repository.findById(id).orElseThrow(() -> new PointNotFoundException(id));

        return assembler.toModel(point);
    }

    @PostMapping("/points")
    public ResponseEntity<?> createPoint(@RequestBody Point point) {
        EntityModel<Point> entityModel = assembler.toModel(repository.save(point));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
