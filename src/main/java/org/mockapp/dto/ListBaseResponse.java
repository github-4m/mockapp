package org.mockapp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public class ListBaseResponse<T> extends BaseResponse {

  private List<T> content = new ArrayList<>();

  public ListBaseResponse() {
  }

  public ListBaseResponse(String errorMessage, String errorCode, boolean success,
      String requestId, List<T> content) {
    super(errorMessage, errorCode, success, requestId);
    this.content = content;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "ListBaseResponse{" +
        "content=" + content +
        '}';
  }
}
