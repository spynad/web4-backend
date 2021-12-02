package app.model;

import app.controller.PointController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PointModelAssembler implements RepresentationModelAssembler<Point, EntityModel<Point>> {

    @Override
    public EntityModel<Point> toModel(Point entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PointController.class).getOnePoint(entity.getId())).withSelfRel(),
                linkTo(methodOn(PointController.class).getAllPoints()).withRel("points"));
    }
}

