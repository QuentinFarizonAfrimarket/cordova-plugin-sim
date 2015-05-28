(function(){
    var cordovaRef = window.PhoneGap || window.cordova || window.Cordova;
	
	function Sim() { }

	Sim.prototype.getSimInfo = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'getSimInfo', []);
	};
  
	Sim.prototype.getAirplaneModeStatus = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'getAirplaneModeStatus', []);
	};
	
	Sim.prototype.enableAirplaneMode = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'enableAirplaneMode', []);
	};
	
	Sim.prototype.disableAirplaneMode = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'disableAirplaneMode', []);
	};
	
	Sim.prototype.toggleAirplaneMode = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'toggleAirplaneMode', []);
	};
	
	Sim.prototype.getServiceState = function (successCallback, errorCallback) {
		return cordovaRef.exec(successCallback, errorCallback, 'Sim', 'getServiceState', []);
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
			window.plugins.sim = new Sim();
		}
	}
	
	if (typeof module != 'undefined' && module.exports) {
        module.exports = new Sim();
    }
	
})();