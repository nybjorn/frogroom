package info.nyberg.frogroom.controllers;

import graphql.schema.DataFetcher;
import info.nyberg.frogroom.repositories.FrogRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

  @Autowired FrogRepositry frogRepositry;

  public DataFetcher getFrogByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String id = dataFetchingEnvironment.getArgument("id");
      return frogRepositry.getOne(Long.parseLong(id));
    };
  }

  public DataFetcher getFrogsDataFetcher() {
    return dataFetchingEnvironment -> {
      return frogRepositry.findAll();
    };
  }
}
