package com.annconia.api.json;

import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Adam scherer
 *
 */
@JsonTypeName("response")
@JsonSerialize(include = Inclusion.NON_NULL)
public interface JsonResponse {

}
