

function chooseHeaderForRequest(token) {
  
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

  return headers;
}


function postData(url = "", data = {}, token, onPost, onError) {

  let headers = chooseHeaderForRequest(token);

  $.ajax({
    contentType: "json",
    type: "post",
    url: url,
    headers: headers,
    data: JSON.stringify(data),
    dataType: "json",
    beforeSend: function () {
      $('#body').hide();
      $('#loader').show();
    },
    complete: function () {
      $('#loader').hide();
      $('#body').show();
    },
    success: onPost,
    error: onError
  });
}

function getData(url = "", data = "", token, onGet, onError) {

  let headers = chooseHeaderForRequest(token);

  let ajx = $.ajax({
    type: "get",
    url: url,
    headers: headers,
    data: data,
    dataType: "json",
    beforeSend: function () {
      $('#body').hide();
      $('#loader').show();
    },
    complete: function (response) {
      $('#loader').hide();
      $('#body').show();
    },
    success: onGet,
    error: onError
  });
  return ajx;
}

function getDataWithoutLoader(url = "", data = "", token, onGet, onError) {

  let headers = chooseHeaderForRequest(token);

  $.ajax({
    type: "get",
    url: url,
    headers: headers,
    data: data,
    dataType: "json",
    success: onGet,
    error: onError
  });
}

function deleteData(url = "", token, onDelete, onError) {

  let headers = chooseHeaderForRequest(token);

  $.ajax({
    type: "delete",
    url: url,
    headers: headers,
    dataType: "json",
    beforeSend: function () {
      $('#body').hide();
      $('#loader').show();
    },
    complete: function () {
      $('#loader').hide();
      $('#body').show();
    },
    success: onDelete,
    error: onError
  });

}

function updateData(url = "", data = {}, token, onPut, onError) {


  let headers = chooseHeaderForRequest(token);

  $.ajax({
    contentType: "json",
    type: "put",
    url: url,
    headers: headers,
    data: JSON.stringify(data),
    dataType: "json",
    beforeSend: function () {
      $('#body').hide();
      $('#loader').show();
    },
    complete: function () {
      $('#loader').hide();
      $('#body').show();
    },
    success: onPut,
    error: onError
  });
}

function specialGetData(url = "", data = {}, token, currentRequest, onGet, onError) {

  let headers = chooseHeaderForRequest(token);

  let ajax = $.ajax({
    type: "get",
    url: url,
    headers: headers,
    data: data,
    dataType: "json",
    beforeSend: function () {
      if (currentRequest != null) {
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
  getDataWithoutLoader,
  postData,
  deleteData,
  updateData,
  specialGetData,
  chooseHeaderForRequest
}

