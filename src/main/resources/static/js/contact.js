console.log("Contact.js")
// const baseUrl = "http://localhost:8080"
// const baseUrl = "http://scm2.us-east-1.elasticbeanstalk.com "
const baseUrl = "https://scm2-production.up.railway.app"

const viewContactModal=document.getElementById('view_contact_modal');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal(){
    contactModal.show();
}

function closeContactModal(){
    contactModal.hide();
}

async function loadContactData(contactId){
    //function call to load data
    console.log(contactId);
    // fetch(`http://localhost:8080/api/contacts/${contactId}`)
    // .then(async(response) => {
    //     const data = await response.json();
    //     console.log(data);
    //     return data;
    // }).catch((error) => {
    //     console.log(error);
    // })

    try{
        const data = await(await fetch(`${baseUrl}/api/contacts/${contactId}`)).json();
        console.log(data);

        document.querySelector('#contact_photo').setAttribute("src", data.picture);
        document.querySelector('#contact_name').innerHTML=data.name;
        document.querySelector('#contact_email').innerHTML=data.email;
        document.querySelector('#contact_phoneNumber').innerHTML=data.phoneNumber;
        document.querySelector('#contact_address').innerHTML=data.address;
        document.querySelector('#contact_des').innerHTML=data.description;
        document.querySelector('#contact_web').innerHTML=data.websiteLink;
        document.querySelector('#contact_web').href=data.websiteLink;
        document.querySelector('#contact_linkedin').innerHTML=data.linkedInLink;
        document.querySelector('#contact_linkedin').href=data.linkedInLink;
        openContactModal();
    }
    catch(error){
        console.log("Error: ", error);
    }
}

//delete contact
async function deleteContact(id){
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#2563eb",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {
            const url = `${baseUrl}/user/contacts/delete/` + id;
            window.location.replace(url);
        }
    });
}
