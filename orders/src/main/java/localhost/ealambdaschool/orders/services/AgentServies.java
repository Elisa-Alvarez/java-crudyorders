package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Agent;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

public interface AgentServies {

    Agent findAgentById(long id);
    Agent save(Agent agent);
    void delete(long agentcode);
    Agent update(Agent agent, long agentcode);
}
