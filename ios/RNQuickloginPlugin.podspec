
Pod::Spec.new do |s|
  s.name         = "RNQuickloginPlugin"
  s.version      = "1.0.0"
  s.summary      = "RNQuickloginPlugin"
  s.description  = "A short description of RNQuickloginPlugin."
  s.homepage     = "https://github.com/yidun/quickpass-react-native"
  #s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "luolihao123456" => "luolihao123456@163.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/yidun/quickpass-react-native.git", :tag => "#{spec.version}"}
  s.source_files  = "RNQuickloginPlugin/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "NTESQuickPass"
  #s.dependency "others"

end

  
