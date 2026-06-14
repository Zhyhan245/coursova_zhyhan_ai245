package ua.opnu.labwork2.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;

import ua.opnu.labwork2.tag.dto.TagCreateRequest;
import ua.opnu.labwork2.tag.dto.TagResponse;
import ua.opnu.labwork2.tag.dto.TagUpdateRequest;
import ua.opnu.labwork2.tag.model.Tag;
import ua.opnu.labwork2.tag.repository.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public TagResponse create(TagCreateRequest request) {

        if (tagRepository.existsByName(request.name())) {
            throw new ConflictOperationException(
                    "Tag with name '" + request.name() + "' already exists"
            );
        }

        Tag tag = new Tag();
        tag.setName(request.name().trim());
        tag.setDescription(request.description().trim());

        return mapToResponse(tagRepository.save(tag));
    }

    @Transactional(readOnly = true)
    public List<TagResponse> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TagResponse findById(Long id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tag not found with id: " + id)
                );

        return mapToResponse(tag);
    }

    @Transactional
    public TagResponse update(Long id, TagUpdateRequest request) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tag not found with id: " + id)
                );

        String newName = request.name().trim();

        if (!tag.getName().equals(newName)
                && tagRepository.existsByName(newName)) {
            throw new ConflictOperationException(
                    "Tag with name '" + newName + "' already exists"
            );
        }

        tag.setName(newName);
        tag.setDescription(request.description().trim());

        return mapToResponse(tagRepository.save(tag));
    }

    @Transactional
    public void delete(Long id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tag not found with id: " + id)
                );

        if (!tag.getTasks().isEmpty()) {
            throw new ConflictOperationException(
                    "Cannot delete tag because it is assigned to tasks"
            );
        }

        tagRepository.delete(tag);
    }

    private TagResponse mapToResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getDescription()
        );
    }
}