#ifndef CALC_ICE
#define CALC_ICE

module Smart
{
  enum style { CARTOON, DUOTONE, ASCII, POLAROID };
  struct CameraState{
    int h;
    int r;
    int zoom;
  }
  exception IllegalArgument{
    string reason;
  }
  exception DeviceFull{
    string reason;
  }
  exception DeviceTurnedOff{
    string reason;
  }

  exception ElementNotFound{
    string reason;
  }

  exception DeviceCannotBeStarted{
    string reason;
  }

  interface SmartDevice{
        void turnOff();
        void turnOn();
  }

  interface SmartFridge extends SmartDevice
  {
    void addElement(string name) throws DeviceFull, DeviceTurnedOff;
    bool containsElement(string name) throws DeviceTurnedOff;
    void removeElement(string name) throws ElementNotFound, DeviceTurnedOff;
  };

  interface SmartCooler extends SmartFridge
  {
    int getTemperature() throws DeviceTurnedOff;
    void setTemperature(int temperature) throws IllegalArgument, DeviceTurnedOff;
  }

  interface SmartDishwasher extends SmartDevice
  {
    bool isFree() throws DeviceTurnedOff;
    void start(int time) throws IllegalArgument, DeviceTurnedOff, DeviceCannotBeStarted;
    void unload() throws DeviceTurnedOff;
  };

   interface SmartPTZCamera extends SmartDevice
   {
     CameraState getPTZ() throws DeviceTurnedOff;
     void setPTZ(CameraState state) throws IllegalArgument, DeviceTurnedOff;
   };

   interface SmartThermalCamera extends SmartPTZCamera
   {
      void setMinTemp(float temperature) throws IllegalArgument, DeviceTurnedOff;
      void setMaxTemp(float temperature) throws IllegalArgument, DeviceTurnedOff;
      float getMaxTemp() throws DeviceTurnedOff;
      float getMinTemp() throws DeviceTurnedOff;

   }

   interface SmartArtisticCamera extends SmartPTZCamera
   {
       style getStyle() throws DeviceTurnedOff;
       void setStyle(style currentStyle) throws IllegalArgument, DeviceTurnedOff;
   }

};

#endif
