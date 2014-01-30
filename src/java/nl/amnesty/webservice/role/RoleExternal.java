package nl.amnesty.webservice.role;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import nl.amnesty.crm.entity.*;
import nl.amnesty.crm.persistence.EMRole;
import nl.amnesty.crm.persistence.EntityManager;
import nl.amnesty.ldap.person.entity.LDAPinetOrgPerson;
import nl.amnesty.sys.controller.CRMLDAPController;

/**
 *
 * @author ed
 */
@WebService()
@Stateless()
public class RoleExternal {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "readviaemail")
    public Role readviaemail(@WebParam(name = "email") String email) {

        // DEBUG
        //Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "RoleExternal: readviaemail email ''{3}''", new Object[]{email});
        if (email==null) {
            return null;
        }
        if (email.contains("@")) {
            // Try to find existing role
            EMRole em = new EMRole();
            Role role = em.roleReadViaEmail(email);
            return role;
        } else {
            return null;
        }
    }

    /**
     *
     * @param forenames
     * @param middle
     * @param surname
     * @param email
     * @return
     */
    @WebMethod(operationName = "createvianameemail")
    public long createvianameemail(@WebParam(name = "forenames") String forenames,
            @WebParam(name = "middle") String middle,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "gender") String gender,
            @WebParam(name = "email") String email) {

        // DEBUG
        Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "RoleExternal: createvianameemail forenames ''{0}'' middle ''{1}'' surname ''{2}'' email ''{3}''", new Object[]{forenames, middle, surname, email});

        return doViaNameEmail(forenames, middle, surname, gender, email);
    }

    private long doViaNameEmail(String forenames, String middle, String surname, String gender, String email) {
        try {
            EntityManager em = new EMRole();
            String source = "";
            Person person = new Person();
            if (forenames==null) {
                forenames="";
            }
            if (middle==null) {
                middle="";
            }
            if (surname==null) {
                surname="";
            }
            if (gender==null) {
                gender="";
            }
            if (email==null) {
                email="";
            }
            person.parseForenames(forenames.replace(";", "").replace("&", "").replace("%", ""));
            person.parseMiddle(middle.replace(";", "").replace("&", "").replace("%", ""));
            person.parseSurname(surname.replace(";", "").replace("&", "").replace("%", ""));
            person.parseGender(gender.replace(";", "").replace("&", "").replace("%", ""));
            Address address = new Address();
            Phone phone = new Phone();
            URL url = new URL();
            url.parseInternetAddress(email.replace(";", "").replace("&", "").replace("%", ""));
            Bankaccount bankaccount = new Bankaccount();
            Role role = new Role(0, source, person, address, phone, url, bankaccount);
            Role rolenew = em.persist(role);
            if (rolenew != null) {

                //DEBUG
                Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "RoleExternal: doViaNameEmail roleid {0}", rolenew.getRoleid());

                return rolenew.getRoleid();
            } else {
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     *
     * @param forenames
     * @param middle
     * @param surname
     * @param city
     * @param email
     * @param mobilephone
     * @return
     */
    @WebMethod(operationName = "createvianamecityemailmobilephone")
    public long createvianamecityemailmobilephone(@WebParam(name = "forenames") String forenames,
            @WebParam(name = "middle") String middle,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "gender") String gender,
            @WebParam(name = "city") String city,
            @WebParam(name = "email") String email,
            @WebParam(name = "mobilephone") String mobilephone) {

        // DEBUG
        Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "RoleExternal: createvianamecityemailmobilephone forenames ''{0}'' middle ''{1}'' surname ''{2}'' gender ''{3}'' city ''{4}'' email ''{5}'' mobilephone ''{6}''", new Object[]{forenames, middle, surname, gender, city, email, mobilephone});

        return doViaNameCityEmailMobilephone(forenames, middle, surname, gender, city, email, mobilephone);
    }

    private long doViaNameCityEmailMobilephone(String forenames, String middle, String surname, String gender, String city, String email, String mobilephone) {
        try {
            EntityManager em = new EMRole();
            String source = "";
            Person person = new Person();
            if (forenames==null) {
                forenames="";
            }
            if (middle==null) {
                middle="";
            }
            if (surname==null) {
                surname="";
            }
            if (gender==null) {
                gender="";
            }
            if (city==null) {
                city="";
            }
            if (email==null) {
                email="";
            }
            if (mobilephone==null) {
                mobilephone="";
            }
            person.parseForenames(forenames.replace(";", "").replace("&", "").replace("%", ""));
            person.parseMiddle(middle.replace(";", "").replace("&", "").replace("%", ""));
            person.parseSurname(surname.replace(";", "").replace("&", "").replace("%", ""));
            person.parseGender(gender.replace(";", "").replace("&", "").replace("%", ""));
            Address address = new Address();
            address.parseCity(city.replace(";", "").replace("&", "").replace("%", ""));
            Phone phone = new Phone();
            phone.parseNumber(mobilephone.replace(";", "").replace("&", "").replace("%", ""));
            URL url = new URL();
            url.parseInternetAddress(email.replace(";", "").replace("&", "").replace("%", ""));
            Bankaccount bankaccount = new Bankaccount();
            Role role = new Role(0, source, person, address, phone, url, bankaccount);
            Role rolenew = em.persist(role);
            if (rolenew != null) {
                return rolenew.getRoleid();
            } else {
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     *
     * @param forenames
     * @param middle
     * @param surname
     * @param street
     * @param houseno
     * @param housenosuffix
     * @param postalcodenumeric
     * @param postalcodealpha
     * @param city
     * @param email
     * @return
     * @exception
     */
    @WebMethod(operationName = "createvianameaddressemail")
    public long createvianameaddressemail(@WebParam(name = "forenames") String forenames,
            @WebParam(name = "middle") String middle,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "gender") String gender,
            @WebParam(name = "street") String street,
            @WebParam(name = "houseno") int houseno,
            @WebParam(name = "housenosuffix") String housenosuffix,
            @WebParam(name = "postalcodenumeric") int postalcodenumeric,
            @WebParam(name = "postalcodealpha") String postalcodealpha,
            @WebParam(name = "city") String city,
            @WebParam(name = "email") String email) {


        // DEBUG
        Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "RoleExternal: createvianameaddressemail forenames ''{0}'' middle ''{1}'' surname ''{2}'' gender ''{3}'' street ''{4}'' houseno ''{5}'' housenosuffix ''{6}'' postalcodenumeric ''{7}'' postalcodealpha ''{8}'' city ''{9}'' email ''{10}''", new Object[]{forenames, middle, surname, gender, street, houseno, housenosuffix, postalcodenumeric, postalcodealpha, city, email});

        return doViaNameAddressEmail(forenames, middle, surname, gender, street, houseno, housenosuffix, postalcodenumeric, postalcodealpha, city, email);
    }

    private long doViaNameAddressEmail(String forenames, String middle, String surname, String gender, String street, int houseno, String housenosuffix, int postalcodenumeric, String postalcodealpha, String city, String email) {
        try {
            EntityManager em = new EMRole();
            String source = "";
            Person person = new Person();
            if (forenames==null) {
                forenames="";
            }
            if (middle==null) {
                middle="";
            }
            if (surname==null) {
                surname="";
            }
            if (gender==null) {
                gender="";
            }
            if (city==null) {
                city="";
            }
            if (email==null) {
                email="";
            }  
            if (street==null) {
                street="";
            }
            if (housenosuffix==null) {
                housenosuffix="";
            }
            if (postalcodealpha==null) {
                postalcodealpha="";
            }
            person.parseForenames(forenames.replace(";", "").replace("&", "").replace("%", ""));
            person.parseMiddle(middle.replace(";", "").replace("&", "").replace("%", ""));
            person.parseSurname(surname.replace(";", "").replace("&", "").replace("%", ""));
            person.parseGender(gender.replace(";", "").replace("&", "").replace("%", ""));
            Address address = new Address();
            address.parseStreet(street.replace(";", "").replace("&", "").replace("%", ""));
            address.setHouseno(houseno);
            address.setHousenosuffix(housenosuffix);
            address.setPostalcodenumeric(postalcodenumeric);
            address.setPostalcodealpha(postalcodealpha.replace(";", "").replace("&", "").replace("%", ""));
            address.parseCity(city.replace(";", "").replace("&", "").replace("%", ""));
            Phone phone = new Phone();
            URL url = new URL();
            url.parseInternetAddress(email.replace(";", "").replace("&", "").replace("%", ""));
            Bankaccount bankaccount = new Bankaccount();
            Role role = new Role(0, source, person, address, phone, url, bankaccount);
            Role rolenew = em.persist(role);
            if (rolenew != null) {
                return rolenew.getRoleid();
            } else {
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createloginaccount")
    public long createloginaccount(@WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "forenames") String forenames,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "city") String city) {

        // DEBUG
        //Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "LDAP: createloginaccount username ''{0}'' password ''{1}'' forenames ''{2}'' surname ''{3}'' city ''{4}''", new Object[]{username, password, forenames, surname, city});

        if (username == null) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with null uid");
            return 0;
        }
        if (username.isEmpty()) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with empty uid");
            return 0;
        }
        if (!URL.isInternetAddress(username)) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with non-internetaddress uid");
            return 0;
        }
        if (password == null) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with null password");
            return 0;
        }
        if (password.isEmpty()) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with empty password");
            return 0;
        }
        if (forenames == null) {
            forenames = "";
        }
        if (surname == null) {
            surname = "";
        }
        if (city == null) {
            city = "";
        }
        // Try to find existing role
        EMRole emrole = new EMRole();
        EntityManager em = new EMRole();
        String email = username;
        Role role = emrole.roleReadViaEmail(email);
        if (role == null) {
            Person person = new Person();
            if (forenames==null) {
                forenames="";
            }
            if (surname==null) {
                surname="";
            }
            if (city==null) {
                city="";
            }
            if (username==null) {
                username="";
            }
            person.parseForenames(forenames.replace(";", "").replace("&", "").replace("%", ""));
            person.parseSurname(surname.replace(";", "").replace("&", "").replace("%", ""));
            Address address = new Address();
            address.parseCity(city.replace(";", "").replace("&", "").replace("%", ""));
            URL url = new URL();
            url.parseInternetAddress(username.replace(";", "").replace("&", "").replace("%", ""));
            role = new Role(0, "", person, address, null, url, null);
            role = em.persist(role);
        } else {
            // DEBUG
            //Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Person (creation) found in CRM for uid {0}.", username);
        }

        LDAPinetOrgPerson ldapinetorgperson = CRMLDAPController.createLoginaccount(role, password);

        if (ldapinetorgperson == null) {
            return 0;
        } else {
            return role.getRoleid();
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateloginaccount")
    public long updateloginaccount(@WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "forenames") String forenames,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "city") String city) {

        // DEBUG
        //Logger.getLogger(RoleExternal.class.getName()).log(Level.INFO, "LDAP: updateloginaccount username ''{0}'' password ''{1}'' forenames ''{2}'' surname ''{3}'' city ''{4}''", new Object[]{username, password, forenames, surname, city});

        if (username == null) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with null uid");
            return 0;
        }
        if (username.isEmpty()) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with empty uid");
            return 0;
        }
        if (!URL.isInternetAddress(username)) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with non-internetaddress uid");
            return 0;
        }
        if (password == null) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with null password");
            return 0;
        }
        if (password.isEmpty()) {
            Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Attempt to create LDAPinetOrgPerson with empty password");
            return 0;
        }
        if (forenames == null) {
            forenames = "";
        }
        if (surname == null) {
            surname = "";
        }
        if (city == null) {
            city = "";
        }
        // Try to find existing role
        EMRole emrole = new EMRole();
        EntityManager em = new EMRole();
        String email = username;
        Role role = emrole.roleReadViaEmail(email);
        if (role == null) {
            Person person = new Person();
            if (forenames==null) {
                forenames="";
            }
            if (surname==null) {
                surname="";
            }
            if (city==null) {
                city="";
            }
            if (username==null) {
                username="";
            }
            person.parseForenames(forenames.replace(";", "").replace("&", "").replace("%", ""));
            person.parseSurname(surname.replace(";", "").replace("&", "").replace("%", ""));
            Address address = new Address();
            address.parseCity(city.replace(";", "").replace("&", "").replace("%", ""));
            URL url = new URL();
            url.parseInternetAddress(username.replace(";", "").replace("&", "").replace("%", ""));
            role = new Role(0, "", person, address, null, url, null);
            role = em.persist(role);
        } else {
            // DEBUG
            //Logger.getLogger(RoleExternal.class.getName()).log(Level.WARNING, "LDAP: Person (update) found in CRM for uid {0}.", username);
        }

        LDAPinetOrgPerson ldapinetorgperson = CRMLDAPController.updateLoginaccount(role, password);

        if (ldapinetorgperson == null) {
            return 0;
        } else {
            return role.getRoleid();
        }
    }
}
