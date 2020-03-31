
function notify(type,msg){
    Swal.fire({
        position: 'center',
        icon: type,
        timerProgressBar: true,
        title: msg,
        showConfirmButton: false,
        timer: 1500
    });
}

export default notify;