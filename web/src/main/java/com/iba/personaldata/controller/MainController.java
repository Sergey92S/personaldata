package com.iba.personaldata.controller;

import com.iba.personaldata.entity.Person;
import com.iba.personaldata.service.IPersonService;
import com.iba.personaldata.util.Position;
import com.iba.personaldata.util.RoleType;
import com.iba.personaldata.util.SortingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@Scope("request")
public class MainController {
    private static final int PARSE_NUMBER = 1000 * 60 * 60 * 24;

    @Autowired
    private IPersonService personService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/", "/person/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/person/afterLogin", method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        String page;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findPersonByLogin(auth.getName());
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("parseNumber", PARSE_NUMBER);
        modelMap.put("currentDate", c.getTime());
        page = new ArrayList<>(auth.getAuthorities()).stream().anyMatch(authority -> RoleType.ADMIN.name().equals(authority.getAuthority())) ? "admin" : "user";
        modelMap.put("person", page.equals("user") ? person : new Person());
        return page;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editProfile(ModelMap modelMap, @RequestParam(value = "personExperience", required = true) String personExperience, @RequestParam(value = "personPosition", required = true) String personPosition, @Valid @ModelAttribute Person personDto, BindingResult br) {
        Person person;
        personDto.setExperience(new Date(Long.parseLong(personExperience)));
        personDto.setPosition(Position.valueOf(personPosition));
        if (!br.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            person = personService.findPersonByLogin(auth.getName());
            if (person.getName().equals(personDto.getName()) && person.getSurname().equals(personDto.getSurname()) && person.getLogin().equals(personDto.getLogin()) && person.getPassword().equals(personDto.getPassword())) {
                modelMap.addAttribute("errorMessage",
                        "Your credentials are the same!");
            } else {
                person = personService.edit(person, personDto);
                if (person != null) {
                    modelMap.addAttribute("successMessage", "Your credentials are successfully changed!");
                    Authentication authentication = new UsernamePasswordAuthenticationToken(person.getLogin(), person.getPassword(), auth.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    person = personDto;
                    modelMap.addAttribute("errorMessage",
                            "This Login is already exist!");
                }
            }
        } else {
            person = personDto;
            modelMap.addAttribute("errorMessage",
                    "Validation is wrong!");
        }
        modelMap.put("person", person);
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("parseNumber", PARSE_NUMBER);
        modelMap.put("currentDate", c.getTime());
        return "user";
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
    public String register(ModelMap modelMap) {
        modelMap.put("person", new Person());
        return "admin";
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    public String register(ModelMap modelMap, @Valid @ModelAttribute Person person, BindingResult br) {
        if (!br.hasErrors()) {
            personService.save(person);
            modelMap.addAttribute("successMessage",
                    "New user is registered!");
        } else {
            modelMap.addAttribute("errorMessage",
                    "Validation is wrong!");
        }
        modelMap.put("person", person);
        return "admin";
    }

    @RequestMapping(value = {"/admin/list", "/admin/list/{sorting}"}, method = RequestMethod.GET)
    public String findPersons(@PathVariable(value = "sorting", required = false) SortingType sortingType) {
        return sortingType != null ? "redirect:/admin/list/"+ sortingType +"/1" : "redirect:/admin/list/"+ request.getParameter("sorting") +"/1";
    }

    @RequestMapping(value = "/admin/list/{sorting}/{page}", method = RequestMethod.GET)
    public String findPersons(ModelMap modelMap, @PathVariable("sorting") SortingType sortingType, @PathVariable Integer page) {
        List<Person> personList = personService.findAll(page, sortingType).getContent();
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("parseNumber", PARSE_NUMBER);
        modelMap.put("currentDate", c.getTime());
        if (!personList.isEmpty()) {
            modelMap.put("persons", personList);
            modelMap.addAttribute("nextPage", ++page);
        } else {
            modelMap.put("persons", personService.findAll(page = 1, sortingType).getContent());
            modelMap.addAttribute("nextPage", ++page);
        }
        modelMap.put("person", new Person());
        modelMap.put("sorting", sortingType);
        return "admin";
    }

    @RequestMapping(value = "/admin/multilist", method = RequestMethod.GET)
    public String findPersonsByParameters() {
        return "redirect:" + request.getContextPath() + "/admin/multilist/" + request.getQueryString().replaceAll("&", ";") + "/1";
    }

    @RequestMapping(value = "/admin/multilist/{paths}/{page}", method = RequestMethod.GET)
    public String findPersonsByParameters(ModelMap modelMap, @MatrixVariable(pathVar = "paths", required = true) Map<String, List<String>> matrixVars, @PathVariable("page") Integer page) {
        Page<Person> personPage;
        List<Person> personList = (personPage = personService.findPersonsByCriteria((matrixVars.get("surname") == null ? null : matrixVars.get("surname").get(0)), (matrixVars.get("position") == null ? null : matrixVars.get("position").get(0).equals("NONE") ? null : Position.valueOf(matrixVars.get("position").get(0))), page)) == null ? new ArrayList<>() : personPage.getContent();
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("parseNumber", PARSE_NUMBER);
        modelMap.put("currentDate", c.getTime());
        if (!personList.isEmpty()) {
            modelMap.put("persons", personList);
            modelMap.addAttribute("surname", matrixVars.get("surname"));
            modelMap.addAttribute("position", matrixVars.get("position"));
            modelMap.addAttribute("nextPage", ++page);
        } else {
            modelMap.put("persons", personList = (personPage = personService.findPersonsByCriteria((matrixVars.get("surname") == null ? null : matrixVars.get("surname").get(0)), (matrixVars.get("position") == null ? null : matrixVars.get("position").get(0).equals("NONE") ? null : Position.valueOf(matrixVars.get("position").get(0))), page = 1)) == null ? new ArrayList<>() : personPage.getContent());
            if (!personList.isEmpty()) {
                modelMap.addAttribute("surname", matrixVars.get("surname"));
                modelMap.addAttribute("position", matrixVars.get("position"));
                modelMap.addAttribute("nextPage", ++page);
            } else {
                modelMap.addAttribute("errorMessage", "Sorry. There is no such persons!");
            }
        }
        modelMap.put("person", new Person());
        return "admin";
    }

    @RequestMapping(value = "/admin/edit/{personId}", method = RequestMethod.GET)
    public String editPerson(@PathVariable("personId") Long id, ModelMap modelMap) {
        Person person = personService.findOne(id);
        modelMap.put("person", person);
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("parseNumber", PARSE_NUMBER);
        modelMap.put("currentDate", c.getTime());
        modelMap.put("positions", Position.values());
        return "edit";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public String editPerson(ModelMap modelMap, @RequestParam(value = "personLogin", required = true) String personLogin, @RequestParam(value = "personExperience", required = true) String personExperience, @RequestParam(value = "personPosition", required = true) String personPosition, @Valid @ModelAttribute Person personDto, BindingResult br) {
        Person person;
        personDto.setExperience(new Date(Long.parseLong(personExperience)));
        personDto.setPosition(Position.valueOf(personPosition));
        if (!br.hasErrors() ) {
            person = personService.findPersonByLogin(personLogin);
            if (person.getName().equals(personDto.getName()) && person.getSurname().equals(personDto.getSurname()) && person.getLogin().equals(personDto.getLogin()) && person.getPassword().equals(personDto.getPassword())) {
                modelMap.addAttribute("errorMessage",
                        "Person's credentials are the same!");
            } else {
                person = personService.edit(person, personDto);
                if (person != null) {
                    modelMap.put("person", person);
                    modelMap.addAttribute("successMessage", "Person's credentials are successfully changed!");
                } else {
                    modelMap.addAttribute("errorMessage",
                            "This Login is already exist!");
                    person = personDto;
                }
            }
        } else {
            modelMap.addAttribute("errorMessage",
                    "Validation is wrong!");
            person = personDto;
        }
        modelMap.put("positions", Position.values());
        modelMap.put("person", person);
        modelMap.put("parseNumber", PARSE_NUMBER);
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        modelMap.put("currentDate", c.getTime());
        return "edit";
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String deletePerson(@PathVariable("id") Long id, ModelMap modelMap) {
        personService.delete(id);
        modelMap.put("person", new Person());
        return "admin";
    }

    @RequestMapping(value = "/person/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(ModelMap modelMap, HttpSession httpSession) {
        SecurityContextHolder.clearContext();
        httpSession.invalidate();
        modelMap.clear();
        modelMap.put("person", new Person());
        return "login";
    }

}
