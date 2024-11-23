import ConfigMenu from "http://localhost:2024/embed/js/configMenu.js";

(function(){

      const currentURL = window.location.href;
      const userAgent = navigator.userAgent;
      const isMobile = /Mobi|Android|iPhone|iPad/i.test(userAgent);
      const isHasMBIDinWD = Boolean(window.MBID);
      const MBID = window.MBID || new URLSearchParams(window.location.search).get('menuId');

      const EmbedMenuRequest = {
          businessCode: MBID,
          deviceType: isMobile ? "PHONE" : "DESKTOP",
          currentUrl: currentURL,
      };
      const EmbedMenuRequestParams = new URLSearchParams(EmbedMenuRequest).toString();

      let API_GET_EMBED_MENU = ConfigMenu.SERVER + "/api/v1/embed/menu?" + EmbedMenuRequestParams;

      if(isHasMBIDinWD){
        // Gửi yêu cầu đến API để lấy cấu hình dựa trên MBID
            fetch(API_GET_EMBED_MENU, {
                method: 'GET',
                })
                .then((response) => response.ok ? response.json() : Promise.reject(response))
                .then((menuDuocHienThi) => {
                    if (menuDuocHienThi) {
                        const menu = menuDuocHienThi.data;
                        // console.log(">>> MENU được hiển thị", menu);
                        EmbedMenu.loadCss(`${EmbedMenu.METU_DOMAIN}/embed/css/metu-menu-config.css`);
                        EmbedMenu.loadCss(`${EmbedMenu.METU_DOMAIN}/embed/css/metu-form-config.css`);
                        ConfigMenu.applyMenuStyles(menu);
                        ConfigMenu.renderMenuHTML(menu);
                    }
                })
            .catch((error) => console.error("ERROR:", error));
      }else {

        const decodedURL = decodeURIComponent(currentURL);
        console.log(decodedURL);

      }

      
})();
