
Pod::Spec.new do |s|
  s.name         = "RNQuickloginPlugin"
  s.version      = "1.0.0"
  s.summary      = "RNQuickloginPlugin"
  s.description  = <<-DESC
                  RNQuickloginPlugin
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/yidun/quickpass-react-native.git", :tag => "master" }
  s.source_files  = "RNQuickloginPlugin/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "NTESQuickPass"
  #s.dependency "others"

end

  
