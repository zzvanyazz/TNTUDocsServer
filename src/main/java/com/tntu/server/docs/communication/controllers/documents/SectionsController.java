package com.tntu.server.docs.communication.controllers.documents;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.mappings.SectionMapper;
import com.tntu.server.docs.communication.models.requests.sections.CreateSectionRequest;
import com.tntu.server.docs.communication.models.requests.sections.UpdateSectionRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.communication.services.auth.CurrentUserService;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.section.SectionNotExistsException;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.services.SectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sections")
public class SectionsController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation("Get all sections")
    @GetMapping("/get")
    public ResponseEntity<?> getSections() {
        var sections = sectionService.getAllSections();
        if (currentUserService.isNotGranted())
            sections.forEach(section -> section.getDocuments().removeIf(DocumentModel::isNotVisible));

        return ResponseEntityFactory.createOk(sections);
    }

    @ApiOperation("Get section")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSection(@PathVariable long id) throws SectionNotExistsException {
        var section = sectionService.getSection(id);
        if (currentUserService.isNotGranted())
            section.getDocuments().removeIf(DocumentModel::isNotVisible);

        return ResponseEntityFactory.createOk(section);
    }

    @ApiOperation("Create new section")
    @PostMapping
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> createSection(@RequestBody CreateSectionRequest request) throws DocsException {
        var section = SectionMapper.toModel(request);
        section = sectionService.createSection(section);

        return ResponseEntityFactory.createOk(section);
    }

    @ApiOperation("Delete section")
    @DeleteMapping(value = "/{id}")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> deletePublicPath(@PathVariable long id) throws SectionNotExistsException {
        sectionService.deleteSection(id);

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Update section")
    @PatchMapping(value = "/{id}")
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> updateSection(@RequestParam UpdateSectionRequest request, @PathVariable long id)
            throws SectionNotExistsException {
        var name = request.getName();
        var section = sectionService.updateSection(id, name);

        return ResponseEntityFactory.createOk(section);
    }

}
