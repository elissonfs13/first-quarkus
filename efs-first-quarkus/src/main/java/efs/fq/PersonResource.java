package efs.fq;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    public List<Person> findAllPerson() {
        return Person.listAll();
    }

    @GET
    @Path("/{id}")
    public Person findPersonById(@PathParam("id") String id) {

        ObjectId personId = new ObjectId(id);
        Optional<Person> personOp = Person.findByIdOptional(personId);

        return personOp.orElseThrow();
    }

    @POST
    @Transactional
    public void createPerson(PersonDTO dto) {
        Person person = new Person();
        person.name = dto.name;
        person.age = dto.age;
        person.createAt = new Date();
        person.modifiedAt = new Date();
        person.persist();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void updatePerson(@PathParam("id") String id, PersonDTO dto) {

        ObjectId personId = new ObjectId(id);
        Optional<Person> personOp = Person.findByIdOptional(personId);

        if (personOp.isPresent()) {
            Person person = personOp.get();
            person.name = dto.name;
            person.age = dto.age;
            person.modifiedAt = new Date();
            person.update();
        } else {
            throw new NotFoundException();
        }

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePerson(@PathParam("id") String id) {

        ObjectId personId = new ObjectId(id);
        Optional<Person> personOp = Person.findByIdOptional(personId);

        personOp.ifPresentOrElse(Person::delete, () -> {
            throw new NotFoundException();
        });

    }
    
}
