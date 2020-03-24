function create_dynamic_HTML_table(
  targetHtmlElementId,
  arrayToPrint,
  confirmerInscription,
  confirmerOuvrier,
  lierUtilisateurClient,
  lierUtilisateurClientTable,
  objectPropertiesToBeSaved,
  thTab
) {
  let div_container = document.getElementById(targetHtmlElementId);
  div_container.innerHTML = "";
  let myTable = document.createElement("table");
 
  myTable.className = "table table-bordered";
 
  div_container.appendChild(myTable);

    let thead = document.createElement("thead");
    myTable.appendChild(thead);
    let headerLine = document.createElement("tr");
    thead.appendChild(headerLine);

  for (let i = 0; i < thTab.length; i++) {
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = thTab[i];
    headerLine.appendChild(th);
  }

  if (confirmerInscription){
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = "Confirmer inscription";
    headerLine.appendChild(th);
  } 

  if (confirmerOuvrier){
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = "Valider inscription en tant que ouvrier";
    headerLine.appendChild(th);
  } 

  if (lierUtilisateurClient){
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = "Lier l'utilisateur à un client";
    headerLine.appendChild(th);
  } 

  if (lierUtilisateurClientTable){
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = "Lier";
    headerLine.appendChild(th);
  } 

  let tbody = document.createElement("tbody");
    myTable.appendChild(tbody);
  for (let x = 0; x < arrayToPrint.length; x++) {
    let myLine = document.createElement("tr");
    tbody.appendChild(myLine);
    const objectToPrint = arrayToPrint[x];
    for (const property in objectToPrint) {
      if (property === "userId") {
        myLine.id = objectToPrint[property];
      }
      if (property !== "N° utilisateur" && property !== "Date d'inscription" && property !== "N° client" && property !== "Rue" && property !== "Boite" && property !== "N° porte") {
      let myCell = document.createElement("td");
      myCell.className = "text-center";
      myCell.innerHTML = objectToPrint[property];
      myLine.appendChild(myCell);
      }
    }

    if(confirmerInscription){
      colonneSupp(confirmerInscription,"Confirmer",myLine,objectPropertiesToBeSaved);
    }
    if(confirmerOuvrier){
      colonneSupp(confirmerOuvrier,"Valider",myLine,objectPropertiesToBeSaved);
    }
    if(lierUtilisateurClient){
      colonneSupp(lierUtilisateurClient,"Lier à un client",myLine,objectPropertiesToBeSaved);
    }
    if(lierUtilisateurClientTable){
      colonneSupp(lierUtilisateurClientTable,"Lier",myLine,objectPropertiesToBeSaved);
    }
  }
}


function colonneSupp(nameFunction,nameButton, myLine,objectPropertiesToBeSaved){
if (nameFunction) {
  let myCell = document.createElement("td");
  myLine.appendChild(myCell);
  let button = document.createElement("button");
  button.value = myLine.id;
  button.className = "btn btn-primary";
  button.innerText = nameButton;
  myCell.className = "text-center";
  myCell.appendChild(button);

  button.addEventListener("click", e => {
    e.preventDefault();
    const id = e.target.value;
    const tr=e.target.parentNode.parentNode; 
    const elements = [...tr.childNodes];
    let data={};
    let i=0;
    if(objectPropertiesToBeSaved)
      {
        objectPropertiesToBeSaved.forEach(property => {
          data[property]=elements[i].innerHTML;
          i++;
        })
      }
      nameFunction(id,data);
  });
}
}

export default create_dynamic_HTML_table;
