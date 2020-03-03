
function postData(url = "", data = {}, token,onPost,onError) {
  let headers;
  if (token)
    headers = {
      "Content-Type": "application/json",
      Authorization: token
    };
  else
    headers = {
      "Content-Type": "application/json"
    };

  $.ajax({
    contentType: "json", 
    type: "post",
    url: url,
    headers: headers,
    data: JSON.stringify(data),
    dataType: "json",
    success: onPost,
    error: onError
  });
  }
  
  function getData(url = "", token, onGet, onError) {
    let headers;
    if (token)
      headers = {
        "Content-Type": "application/json",
        Authorization: token
      };
    else
      headers = {
        "Content-Type": "application/json"
      };

    $.ajax({
    type: "get",
    url: url,
    headers: headers,
    dataType: "json",
    success: onGet,
    error: onError
  });
}

  function deleteData(url = "", token,onDelete,onError) {
    let headers;
    if (token)
      headers = {
        "Content-Type": "application/json",
        Authorization: token
      };
    else
      headers = {
        "Content-Type": "application/json"
      };

    $.ajax({
    type: "delete",
    url: url,
    headers: headers,
    dataType: "json",
    success: onDelete,
    error: onError
  });    
    
  }

  function updateData(url = "", data = {}, token,onPut,onError) {
    let headers;
  if (token)
    headers = {
      "Content-Type": "application/json",
      Authorization: token
    };
  else
    headers = {
      "Content-Type": "application/json"
    };

  $.ajax({
    contentType: "json",
    type: "put",
    url: url,
    headers: headers,
    data: JSON.stringify(data),
    dataType: "json",
    success: onPut,
    error: onError
  });    
  }

  export {
      getData,
      postData,      
      deleteData,
      updateData,
  }