
class InteractRequest {
    constructor(menuId, buttonId, currentUrlId) {
        this.currentUrlId = currentUrlId;
        this.menuId = menuId;
        this.buttonId = buttonId;
    }
}

export default class ConfigMenu {

  static SERVER =  "http://localhost:1919";

  static currentUrlId = "";

  static applyMenuStyles(menu) {
      this.currentUrlId = menu.currentUrlId;
      document.documentElement.style.setProperty("--metu-menu-background-color", menu.backgroundColor);
      document.documentElement.style.setProperty("--metu-menu-text-color", menu.textColor);
      document.documentElement.style.setProperty("--metu-button-icon-color", menu.iconColor);
      document.documentElement.style.setProperty("--metu-menu-opacity", menu.opacity);
      document.documentElement.style.setProperty("--metu-form-title-background-color", menu.backgroundColor);
      document.documentElement.style.setProperty("--metu-form-title-text-color",  menu.textColor);
  }

  static isSvg(url) {
    return url.toLowerCase().endsWith('.svg');
  }

  static renderMenuHTML(menu) {
    const metuMenuHTML = `
    <metu-div class="metu-menu ${menu.shapeClassName} ${menu.positionClassName}">
        ${menu.buttons
            .map(
                (button, index) => `
                <div class="metu-button" tool-tip="${button.tooltip}  ${ (button.type === "HOTLINE" || button.type === "SMS" ) ? `${button.data.tel}`:''}"
                     data-color="${button.type.toLowerCase()}"
                     id="metu-button-${menu.id}-${index}">
                      ${ConfigMenu.isSvg(button.iconUrl) 
                            ? `<img class="metu-svg-icon" src="${button.iconUrl}" alt="${button.name}">`
                            : `<img class="metu-png-icon" src="${button.iconUrl}" alt="${button.name}">`
                        }
                    <span class="metu-txt-btn" tool-tip="${button.title}"></span>
                    
                </div>
                `
            )
            .join("")}
    </metu-div>`;
    document.body.insertAdjacentHTML("beforeend", metuMenuHTML);
    this.bindButtonEvents(menu.buttons, menu.id);
  }

    static bindButtonEvents(buttons, menuId) {
        buttons.forEach((button, index) => {
            const interactRequest = new InteractRequest(menuId, button.id, this.currentUrlId);
            const buttonElement = document.getElementById(`metu-button-${menuId}-${index}`);
            switch (button.type) {
                case "TEXT":
                    buttonElement.addEventListener("click", () =>
                        this.saveInteract(interactRequest)
                    );
                    break;
                case "LINK":
                    buttonElement.addEventListener("click", () => {
                        this.navigateToLink(button.data);
                        this.saveInteract(interactRequest);
                        }
                    );
                    break;
                case "HOTLINE":
                    buttonElement.addEventListener("click", () => {
                        this.call(button.data.tel);
                        this.saveInteract(interactRequest);
                        }
                    );
                    break;
                case "SMS":
                    buttonElement.addEventListener("click", () => {
                        this.sendMessage(button.data);
                        this.saveInteract(interactRequest);
                        }
                    );
                    break;
                case "LIVE_CHAT":
                    buttonElement.addEventListener("click", () => {
                        this.showLiveChat(button.data);
                        this.saveInteract(interactRequest);
                        }
                    );
                    break;
                default:
                    buttonElement.addEventListener("click", () => {
                        this.showForm(button.type, button.data);
                        this.saveInteract(interactRequest);
                        }
                    );
                    break;
            }
        });
    }

    static call(tel) {
        if (tel) {
            window.location.href = `tel:${tel}`;
        }
    }

    static sendMessage(buttonData) {
        if (buttonData?.tel) {
            const encodedMessage = encodeURIComponent(buttonData.prepeared_messenge);
            window.location.href = `sms:${buttonData.tel}?&body=${encodedMessage}`;
        }
    }

    static navigateToLink(buttonData) {
        let redirectUrl = buttonData.redirect_link;
        if (!redirectUrl.startsWith("http://") && !redirectUrl.startsWith("https://")) {
            redirectUrl = `https://${redirectUrl}`;
        }
        if (buttonData.target === "_blank") {
            window.open(redirectUrl, "_blank");
        } else {
            window.location.href = redirectUrl;
        }
    }

    static showForm(type, formConfig) {
        const overlay = document.querySelector(".metu-widget-form");
        if (overlay) {
            return;
        }
        let formHTML;

        if (type === "CALL_NOW") {
            const contactList = formConfig.contacts.map(contact => `
                <div class="metu-form-field">
                    <p>${contact.agent}</p>
                    <p><span class="metu-form-icon-work"></span> ${contact.office}</p>
                    <p>
                        <span class="metu-form-icon-phone"></span>
                        <a href="tel:${contact.tel}">${contact.tel}</a>
                    </p>
                </div>
            `).join('');
            formHTML = `
                <metu-form class="metu-widget-form">
                    <div class="metu-form-title">
                        Gọi cho chúng tôi
                    </div>
                    <div class="metu-form-content">
                        ${formConfig?.reminder != undefined && formConfig?.reminder != '' ? `<div class="metu-form-note">${formConfig?.reminder}</div>` : ''}
                        <div class="metu-form-main">
                            ${contactList}
                        </div>
                    </div>
                    <div class="metu-form-footer">
                        <button class="metu-form-button" onclick="ConfigMenu.closeForm()">Hủy</button>
                    </div>
                </metu-form>
            `;
            
        } else {
            const fields = formConfig.fields.map((field, index) => `
                <div class="metu-form-field">
                    <input type="${field.type || 'text'}"
                           name="${field.title}"
                           placeholder="${field.title} ${field.require ? '(bắt buộc)' : ''}"  ${field.require ? 'required' : ''}
                           oninput="ConfigMenu.validate('${field.type || 'text'}', this)"
                           />
                    <div class="metu-form-error">error</div>
                </div>
            `).join('');
            formHTML = `
                <form class="metu-widget-form" action="" method="post">
                    <div class="metu-form-title">
                        ${formConfig.form_title}
                    </div>
                    <div class="metu-form-content">
                        ${formConfig?.reminder != undefined && formConfig?.reminder != '' ? `<div class="metu-form-note">${formConfig?.reminder}</div>` : ''}
                        ${fields}
                    </div>
                    <div class="metu-form-footer">
                        <button class="metu-form-button" onclick="ConfigMenu.submitForm('${formConfig?.redirect_link}','${formConfig?.target}', event)">Gửi</button>
                        <button class="metu-form-button" onclick="ConfigMenu.closeForm()">Hủy</button>
                    </div>
                </form>
            `;
        }
    
        document.body.insertAdjacentHTML("beforeend", formHTML);
    }
    

    static validate(type, ele){
        const nextEl = ele.nextElementSibling; 
        nextEl.style.opacity = "0";
        const isRequired = ele.hasAttribute('required');
        if (ele.value === "" && isRequired) {
            nextEl.innerText = "Không được để trống";
            nextEl.style.opacity = "1";
            return false;
        }
        if (type === 'number' && isNaN(ele.value) && ele.value != '') {
            nextEl.innerText = "Vui lòng nhập số điện thoại hợp lệ";
            nextEl.style.opacity = "1";
            return false;
        }
        if (type === 'email' && ! /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(ele.value) && ele.value != '') {
            nextEl.innerText = "Vui lòng nhập email hợp lệ";
            nextEl.style.opacity = "1";
            return false;
        }
        if (type === 'tel' && ! /^(\+84|0)([3|5|7|8|9][0-9]{8})$/.test(ele.value) && ele.value != '') {
            nextEl.innerText = "Vui lòng nhập số điện thoại hợp lệ";
            nextEl.style.opacity = "1";
            return false;
        }
        return true;
    }

    static submitForm(redirectUrl, target, e) {
        e.preventDefault();
        const inputs = document.querySelectorAll(".metu-widget-form input");
        let data = [];
        let isFormValid = true;
        inputs.forEach((input) => {
            if(!this.validate(input.type, input)){
                isFormValid = false;
            }else{
                data.push({
                    title: input.name,
                    value: input.value,
                });
            }
        });
        if(!isFormValid){
            return;
        }
        // console.log("Dữ liệu form:", data);

        fetch("https://api.ipify.org?format=json")
            .then((response) => response.json())
            .then((data) => console.log("User IP:", data.ip));

        if(redirectUrl === ''){
            return;
        }
        if (!redirectUrl.startsWith("http://") && !redirectUrl.startsWith("https://")) {
            redirectUrl = `https://${redirectUrl}`;
        }
        if (target === "_blank") {
            window.open(redirectUrl, "_blank");
        } else {
            window.location.href = redirectUrl;
        }
        
        this.closeForm();
    }

    static closeForm() {
        const overlay = document.querySelector(".metu-widget-form");
        if (overlay) {
            overlay.remove();
        }
    }

    
    static showLiveChat(buttonData) {
        console.log(`Showing live chat`);
    }

    static saveInteract(interactRequest) {
        if(this.currentUrlId){
            const API_SAVE_INTERACT_INFO = this.SERVER + "/api/v1/embed/save-interact-info";
            this.post(API_SAVE_INTERACT_INFO, interactRequest)
            .then(response => {
                // console.log('Success:', response);
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    }

    static async post(url, data) {
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
            if (!response.ok) {
                throw new Error(`Request failed with status ${response.status}`);
            }
            const result = await response.json();
            return result;
        } catch (error) {
            throw error;
        }
    }

}

window.ConfigMenu = ConfigMenu;