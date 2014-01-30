/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webservice.role;

import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import nl.amnesty.crm.entity.Role;
import nl.amnesty.crm.persistence.EMRole;
import nl.amnesty.crm.persistence.EntityManager;

/**
 *
 * @author ed
 * algemene webservices uitgezet uit beveiligingsoogpunt
 */
@WebService()
@Stateless()
public class RoleCRUD {

    /**
     * Web service operation
     
    @WebMethod(operationName = "dummy")
    public String dummy() {
        //TODO write your implementation code here:
        return null;
    }
    */

    /**
     * Web service operation
     
    @WebMethod(operationName = "create")
    public long create(@WebParam(name = "Role") Role role) {
        //TODO write your implementation code here:
        //Role newrole = RoleController.create(role);
        //return newrole.getRoleid();
        return 1234567;
    }
    */

    /**
     * Web service operation
     */
    @WebMethod(operationName = "read")
    public Role read(@WebParam(name = "Roleid") long roleid) {
        EntityManager em = new EMRole();
        return em.find(roleid);
    }
    

    /**
     * Web service operation
     
    @WebMethod(operationName = "update")
    public boolean update(@WebParam(name = "Role") Role role) {
        //TODO write your implementation code here:
        return false;
    }
    */

    /**
     * Web service operation
     
    @WebMethod(operationName = "delete")
    public boolean delete(@WebParam(name = "Roleid") long roleid) {
        //TODO write your implementation code here:
        return false;
    }
    */
}
