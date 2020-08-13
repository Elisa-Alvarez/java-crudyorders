package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Agent;
import localhost.ealambdaschool.orders.models.Customer;
import localhost.ealambdaschool.orders.repo.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "agents")
public class AgentServicesImpl implements AgentServies {

    @Autowired
    AgentsRepository agentrepos;

    @Override
    public Agent findAgentById(long id)
    {
        return agentrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found!"));
    }


    //New Agent Object
    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        if (agent.getAgentcode() != 0){
            agentrepos.findById(agent.getAgentcode())
                    .orElseThrow(()-> new EntityNotFoundException("Agent" + agent.getAgentcode() + "Not Found!"));
        }
        Agent newAgent = new Agent();
        newAgent.setAgentname(agent.getAgentname());
        newAgent.setCommission(agent.getCommission());
        newAgent.setCountry(agent.getCountry());

        //one to many
         newAgent.getCustomer().clear();
        for (Customer c: agent.getCustomer())
        {
            Customer newCustomer = new Customer();
            newCustomer.setCustcountry(c.getCustcountry());

            newAgent.getCustomer().add(newCustomer);
        }


        return agentrepos.save(newAgent);
    }

    @Override
    public void delete(long agentcode) {

    }

    @Override
    public Agent update(Agent agent, long agentcode) {
        return null;
    }
 /*

    @Transactional
    @Override
  public void delete(long agentcode){
        if(agentcode.findById(agentcode).isPresent())
        {
            agentcode.deleteById(agentcode);
        }else
        {
            throw new EntityNotFoundException("Agent" + agentcode + "Not Found!");
        }
    }

   */

}
