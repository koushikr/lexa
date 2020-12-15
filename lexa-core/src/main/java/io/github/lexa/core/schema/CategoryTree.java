package io.github.lexa.core.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import io.github.jforrest.Edge;
import io.github.jforrest.Forrest;
import io.github.jforrest.Vertex;
import io.github.lexa.core.schema.profiles.CategoryProfile;
import lombok.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by koushikr on 19/09/17.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryTree extends Forrest {

    private String serviceType;

    //Map of categoryId, to categoryProfile
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, CategoryProfile> profiles = new HashMap<>();

    private long updatedAt;

    @Builder
    protected CategoryTree(String serviceType, List<CategoryProfile> allProfiles) {
        super(
                allProfiles.stream().collect(Collectors
                        .toMap(CategoryProfile::getCategoryId, each -> new Vertex(each.getCategoryId())))
        );

        this.serviceType = serviceType;
        this.profiles = allProfiles.stream()
                .collect(Collectors.toMap(CategoryProfile::getCategoryId, Function.identity()));
        this.updatedAt = allProfiles.isEmpty() ? 0
                : allProfiles.stream().map(CategoryProfile::getUpdatedAt).max(Long::compare).get();
    }

    /**
     * A utility method to construct a CategoryTree. Also does the validation.
     *
     * @return The categoryGraph associated with the given categoryProfiles and the said categoryGroup
     */
    public static CategoryTree build(String serviceType, List<CategoryProfile> profiles) {
        CategoryTree categoryTree = CategoryTree.builder()
                .serviceType(serviceType)
                .allProfiles(profiles)
                .build();

        categoryTree.validateServiceTree();

        return categoryTree;
    }

    /**
     * A function to add all the edges and validate the dag.
     */
    private void validateServiceTree() {
        this.addEdges(this.getExhaustiveEdges());
    }

    /**
     * A function to get the categoryProfile given the categoryId;
     */
    public CategoryProfile getCategoryProfile(String categoryId) {
        return this.getProfiles().get(categoryId);
    }

    /**
     * A function to get the children of the given categoryId.
     */
    public List<String> getChildCategories(String categoryId) {
        return this.getNodes()
                .get(categoryId)
                .getOutgoingEdges()
                .stream()
                .map(Edge::getDestination)
                .collect(Collectors.toList());
    }

    /**
     * Returns an ArrayList containing all vertices that have no incoming edges in the dag.
     *
     * @return an ArrayList containing all vertices that have no incoming edges in the dag.
     */
    @JsonProperty("rootCategories")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<String> getOriginCategories() {
        return this.getNodes()
                .values()
                .stream()
                .filter(
                        categoryNode -> categoryNode.getIncomingEdges().isEmpty()
                )
                .map(
                        Vertex::getId
                )
                .collect(Collectors.toList());
    }

    /**
     * A function to get all the edges from the profile data that we copied. The map<string, vertex>
     * doesn't have the incoming and outgoing filled yet,
     * <p>
     * The addEdges method will do the filling up of DAG with error handling
     *
     * @return The set of exhaustive edges.
     */
    @JsonIgnore
    public Set<Edge> getExhaustiveEdges() {
        Set<Edge> edges = new HashSet<>();

        for (CategoryProfile profile : profiles.values()) {
            edges.addAll(profile
                    .getOutgoingCategories()
                    .stream()
                    .map(
                            each -> new Edge(
                                    profile.getCategoryId(),
                                    each
                            )).collect(Collectors.toSet()));
        }

        return edges;
    }

    /**
     * A function to get the subTree given the categoryId
     *
     * @return The categoryGraph.
     */
    public CategoryTree getSubTree(String categoryId) throws Exception {
        if (this.getNodes().isEmpty()) {
            return new CategoryTree(this.getServiceType(), Lists.newArrayList());
        }

        List<Vertex> nodes = this.subTree(categoryId);
        return CategoryTree.build(
                serviceType,
                nodes.stream().map(
                        each -> this.getCategoryProfile(each.getId())
                ).collect(Collectors.toList())
        );
    }

    @JsonIgnore
    public boolean isRootNode(String categoryId) {
        return getOriginCategories().stream().anyMatch(each -> each.equalsIgnoreCase(categoryId));
    }
}
