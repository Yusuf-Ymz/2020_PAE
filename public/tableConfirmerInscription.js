function create_dynamic_HTML_table(
  targetHtmlElementId,
  arrayToPrint,
  confirmerInscription,
  confirmerOuvrier,
  lierUtilisateurClient,
  objectPropertiesToBeSaved
) {
  let div_container = document.getElementById(targetHtmlElementId);
  div_container.innerHTML = "";
  let myTable = document.createElement("table");
  myTable.className = "table";
  myTable.className = "mt-10";
  div_container.appendChild(myTable);

  let thead = document.createElement("thead");
    myTable.appendChild(thead);
    let headerLine = document.createElement("tr");
    thead.appendChild(headerLine);
  const objectToPrint = arrayToPrint[0];
  for (const property in objectToPrint) {
    if (property === "nom" || property === "prenom" 
                || property === "pseudo" || property === "ville" || property === "email") {
    let th = document.createElement("th");
    th.className = "text-center";
    th.innerHTML = property;
    headerLine.appendChild(th);
    }
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

  let tbody = document.createElement("tbody");
    myTable.appendChild(tbody);
  for (let x = 0; x < arrayToPrint.length; x++) {
    let myLine = document.createElement("tr");
    tbody.appendChild(myLine);
    const objectToPrint = arrayToPrint[x];
    for (const property in objectToPrint) {
      if (property === "id") {
        myLine.id = objectToPrint[property];
      }
      if (property === "nom" || property === "prenom" 
                || property === "pseudo" || property === "ville" || property === "email") {
      let myCell = document.createElement("td");
      myCell.className = "text-center";
      myCell.innerHTML = objectToPrint[property];
      myLine.appendChild(myCell);
      }
    }

    colonneSupp(confirmerInscription,"Confirmer",myLine);
    colonneSupp(confirmerOuvrier,"Valider",myLine);
    colonneSupp(lierUtilisateurClient,"Lier à un client",myLine);
  }
}


function colonneSupp(nameFunction,nameButton, myLine){
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
    let item={};
    let i=0;
    if(objectPropertiesToBeSaved)
      {
        objectPropertiesToBeSaved.forEach(property => {
          item[property]=elements[i].innerHTML;
          i++;
        })
      }
      nameFunction(id,item);
  });
}
}

export default create_dynamic_HTML_table;
