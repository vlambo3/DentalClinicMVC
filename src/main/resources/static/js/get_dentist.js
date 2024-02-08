window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de odontólogos con el método GET
      //nos devolverá un JSON con una colección de odontólogos
      const url = '/odontologos';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
      //recorremos la colección de odontólogos del JSON
         for(dentist of data){
            //por cada odontólogo armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el odontólogo
            var table = document.getElementById("dentistTable");
            var dentistRow =table.insertRow();
            let tr_id = 'tr_' + dentist.id;
            dentistRow.id = tr_id;

            //por cada odontólogo creamos un boton delete que agregaremos en cada fila para poder eliminar la misma
            //dicho boton invocara a la funcion de java script deleteByKey que se encargará
            //de llamar a la API para eliminar un odontólogo
            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + dentist.id + '\"' +
                                      ' type="button" onclick="deleteBy('+dentist.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

            //por cada odontólogo creamos un boton que muestra el id y que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar el odontólogo que queremos
            //modificar y mostrar los datos de la misma en un formulario.
            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + dentist.id + '\"' +
                                      ' type="button" onclick="findBy('+dentist.id+')" class="btn btn-info btn_id">' +
                                      dentist.id +
                                      '</button>';

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos del odontólogo
            //como ultima columna el boton eliminar
            dentistRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_id\">' + dentist.id + '</td>' +
                    '<td class=\"td_registration\">' + dentist.registration + '</td>' +
                    '<td class=\"td_name\">' + dentist.name.toUpperCase() + '</td>' +
                    '<td class=\"td_lastName\">' + dentist.lastName.toUpperCase() + '</td>' +
                    '<td>' + deleteButton + '</td>'
                    ;

        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/dentistList.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })