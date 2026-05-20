package co.sena.cimm.adso.vacunacionspring.controller;

import co.sena.cimm.adso.vacunacionspring.model.Paciente;
import co.sena.cimm.adso.vacunacionspring.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        return "pacientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/formulario";
    }


    @PostMapping
    public String guardar(@ModelAttribute Paciente paciente,
                          RedirectAttributes redirectAttr) {
        pacienteService.guardar(paciente);
        redirectAttr.addFlashAttribute("mensaje", "Paciente guardado correctamente");
        return "redirect:/pacientes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", pacienteService.buscarPorId(id));
        return "pacientes/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute Paciente paciente,
                             RedirectAttributes redirectAttr) {
        paciente.setId(id);
        pacienteService.guardar(paciente);
        redirectAttr.addFlashAttribute("mensaje", "Paciente actualizado");
        return "redirect:/pacientes";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttr) {
        pacienteService.eliminar(id);
        redirectAttr.addFlashAttribute("mensaje", "Paciente eliminado");
        return "redirect:/pacientes";
    }
}