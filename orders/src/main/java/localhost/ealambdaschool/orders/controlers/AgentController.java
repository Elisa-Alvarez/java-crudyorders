package localhost.ealambdaschool.orders.controlers;

import localhost.ealambdaschool.orders.models.Agent;
import localhost.ealambdaschool.orders.services.AgentServies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("agents")
public class AgentController {

    @Autowired
   private AgentServies agentServies;


    //http://localhost:2019/agents/agent/9

    @GetMapping(value = "/agent/{agentcode}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long agentcode)
    {
        Agent myAgent = agentServies.findAgentById(agentcode);
        return new ResponseEntity<>(myAgent, HttpStatus.OK);
    }


    @PostMapping(value = "/agent/{agentcode}", consumes = "application/json")
    public ResponseEntity<?> addNewAgent(@Valid @RequestBody Agent newAgent)
    {
        newAgent.setAgentcode(0);
        newAgent = agentServies.save(newAgent);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAgentURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newAgent.getAgentcode())
                .build()
                .toUri();
                responseHeaders.setLocation(newAgentURI);

                return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //Patch (small amount of data)
    @PatchMapping(value = "/agent/{agentcode}", consumes = "application/json")
    public ResponseEntity<?> updateSomeAgent (@RequestBody Agent updateSomeAgent, @PathVariable long agentcode)
    {
        agentServies.update(updateSomeAgent, agentcode);
                return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = "/agent/{agentcode}", consumes = "application/json")
    public ResponseEntity<?> updateAgent(@Valid @RequestBody Agent updateAgent, @PathVariable long agentcode)
    {
        updateAgent.setAgentcode(agentcode);
        agentServies.save(updateAgent);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/agent/{agentcode}", consumes = "application/json")
    public ResponseEntity<?> deleteById(@PathVariable long agentcode)
    {
        agentServies.delete(agentcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
