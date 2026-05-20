package co.sena.cimm.adso.vacunacionspring.controller;

import co.sena.cimm.adso.vacunacionspring.model.Vacuna;
import co.sena.cimm.adso.vacunacionspring.service.VacunaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("vacunas", vacunaService.listarTodas());

        return "vacunas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute("vacuna", new Vacuna());

        return "vacunas/formulario";
    }

    @PostMapping("/new")
    public String guardar(@ModelAttribute Vacuna vacuna,
                          RedirectAttributes redirectAttributes) {

        vacunaService.guardar(vacuna);

        redirectAttributes.addFlashAttribute("mensaje", "Vacuna guardada correctamente");

        return "redirect:/vacunas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          Model model) {

        model.addAttribute("vacuna", vacunaService.buscarPorId(id));

        return "vacunas/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable int id,
                             @ModelAttribute Vacuna vacuna,
                             RedirectAttributes redirectAttributes) {

        vacuna.setId(id);

        vacunaService.guardar(vacuna);

        redirectAttributes.addFlashAttribute("mensaje", "Vacuna actualizada correctamente");

        return "redirect:/vacunas";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {

        vacunaService.eliminar(id);

        redirectAttributes.addFlashAttribute("mensaje", "Vacuna eliminada correctamente");

        return "redirect:/vacunas";
    }
}