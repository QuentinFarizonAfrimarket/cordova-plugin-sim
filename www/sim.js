(function(){
    var cordovaRef = window.PhoneGap || window.cordova || window.Cordova;
	
	function sim() { }

	sim.prototype.getSimInfo = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'getSimInfo', []);
	};
  
	sim.prototype.getAirplaneModeStatus = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'getAirplaneModeStatus', []);
	};

	if (cordovaRef && cordovaRef.addConstructor) {
        cordovaRef.addConstructor(init);
    }
    else {
        init();
    }

	function init(){
		if (!window.plugins) {
			window.plugins = {};
	    }

		if (!window.plugins.sim){
			window.plugins.sim = new sim();
		}
	}
	
	if (typeof module != 'undefined' && module.exports) {
        module.exports = new sim();
    }
	
})();