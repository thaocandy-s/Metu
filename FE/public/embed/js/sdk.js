// EmbedMenu.js
export default class EmbedMenu {

  static METU_DOMAIN = "http://localhost:2024";

  static loadCss(cssUrl) {
      const linkElement = document.createElement("link");
      linkElement.rel = "stylesheet";
      linkElement.href = cssUrl;
      document.head.appendChild(linkElement);
  }

  static loadScripts(scripts) {
    scripts.forEach(({ src, type = "text/javascript" }) => {
        const scriptElement = document.createElement("script");
        scriptElement.src = src;
        scriptElement.type = type;
        scriptElement.async = true;
        document.head.appendChild(scriptElement);
    });
}

  static loadEmbedMenuConfigJs() {
      this.loadScripts([
        { src: `${this.METU_DOMAIN}/embed/js/showEmbedMenu.js`, type: "module"}
      ]);
  }
  
  static {
      this.loadEmbedMenuConfigJs();
  }

}

window.EmbedMenu = EmbedMenu;


