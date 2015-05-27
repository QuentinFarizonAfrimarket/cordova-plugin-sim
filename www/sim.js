var sim = {
  getSimInfo: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'Sim', 'getSimInfo', []);
  }
  
  getAirplaneModeStatus: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'Sim', 'getAirplaneModeStatus', []);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.sim = sim;
  return window.plugins.sim;
});
