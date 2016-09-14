package controllers;


import javax.inject.Inject;

import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CacheController extends Controller {

  private CacheApi cache;

  @Inject
  public CacheController(CacheApi cache) {
    this.cache = cache;
  }

  public Result putUser(String name) {
    cache.set(name, name, 60 * 100);
    return ok(cache.get(name).toString());
  }

  public Result listCacheUser() {
    return ok(Json.toJson(cache));
  }
}
