
function postData(url = "", data = {}, token, onPost, onError) {
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
    beforeSend: function(){
      $('#loader').show();
    },
    complete: function(){ 
      $('#loader').hide();
    },
    success: onPost,
    error: onError
  });
}

function getData(url = "", data = "", token, onGet, onError) {
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
    data: data,
    dataType: "json",
    beforeSend: function(){
      $('#loader').show();
    },
    complete: function(){
      $('#loader').hide();
    },
    success: onGet,
    error: onError
  });
}

function deleteData(url = "", token, onDelete, onError) {
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
    beforeSend: function(){
      $('#loader').show();
    },
    complete: function(){
      $('#loader').hide();
    },
    success: onDelete,
    error: onError
  });

}

function updateData(url = "", data = {}, token, onPut, onError) {
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
    beforeSend: function(){
      $('#loader').show();
    },
    complete: function(){
      $('#loader').hide();
    },
    success: onPut,
    error: onError
  });
}

function specialGetData(url = "", data = {}, token,currentRequest ,onGet, onError) {
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

  let ajax = $.ajax({
    type: "get",
    url: url,
    headers: headers,
    data: data,
    dataType: "json",
    beforeSend : function()    {           
      if(currentRequest != null) {
          currentRequest.abort();
      }
    },
    success: onGet,
    error: onError
  });

  return ajax;
}
export {
  getData,
  postData,
  deleteData,
  updateData,
  specialGetData
}